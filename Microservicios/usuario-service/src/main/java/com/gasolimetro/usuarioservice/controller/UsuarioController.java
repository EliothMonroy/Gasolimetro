package com.gasolimetro.usuarioservice.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gasolimetro.usuarioservice.ConstantesNegocio;
import com.gasolimetro.usuarioservice.entity.EstadoUsuario;
import com.gasolimetro.usuarioservice.entity.Login;
import com.gasolimetro.usuarioservice.entity.ResponseGeneric;
import com.gasolimetro.usuarioservice.entity.Usuario;
import com.gasolimetro.usuarioservice.repository.LoginRepository;
import com.gasolimetro.usuarioservice.repository.UsuarioRepository;


@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private Environment env;
	
	private static final Random RANDOM = new SecureRandom();
	
	@GetMapping("/saludo")
	 public String hello() {
	    return "Gasolimetro desde GCP!";
	 }
	
	@GetMapping("/usuarios")
	public List<Usuario> getUsuarios(){
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		//Agregamos el puerto para verificar el balanceo de carga de los microservicios
		for(Usuario u: usuarios) {
			u.setPuerto(Integer.parseInt(env.getProperty("local.server.port")));
		}
		return usuarios;
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity<ResponseGeneric<Usuario>> registrarUsuario(@Valid @RequestBody Usuario user){		
		ResponseGeneric<Usuario> response =  new ResponseGeneric<Usuario>();
		//RN - Buscamos un usuario con el mismo correo
		Integer existsUsuario = usuarioRepository.findUsuarioByEmail(user.getLogin().getEmail());
		if(existsUsuario == ConstantesNegocio.REGISTER_EXISTS) {
			response.setMsg(ConstantesNegocio.MSG_USUARIO_EXISTS);
			return new ResponseEntity<ResponseGeneric<Usuario>>(response,HttpStatus.OK);
		} else {
			//Guardamos un usuario
			Usuario usuario = usuarioRepository.save(user);

			//Guardamos la credenciales ingresadas por el usuario
			usuario.getLogin().setUsuario(usuario);
			loginRepository.save(usuario.getLogin());
			
			try {
				//Envío del email 
				
				Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
				String url = "172.20.10.6";
				
                for (; n.hasMoreElements();){
                	NetworkInterface e = n.nextElement();
                    Enumeration<InetAddress> a = e.getInetAddresses();
                    if(e.getName().equals("wlp7s0")) {
                    	int aux = 1;
                    	for (; a.hasMoreElements();){
                    		InetAddress addr = a.nextElement();
                    		if(aux == 2) {
                    			url = "http://"+addr.getHostAddress()+":"
                						+env.getProperty("local.server.port")+"/verification/"+usuario.getIdUsuario();
                    			break;
                    		}
                        	aux++;
                        }
                    }
                }
						
				sendmail(usuario.getLogin().getEmail(), ConstantesNegocio.EMAIL_VALIDAR_USUARIO, 
						usuario.getNombre()+", "+ConstantesNegocio.EMAIL_CREAR_USUARIO + url);
				
			} catch (Exception e) {
				return new ResponseEntity<ResponseGeneric<Usuario>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setMsg(ConstantesNegocio.MSG_USUARIO_CREATE);
			response.setObject(usuario);
			
			return new ResponseEntity<ResponseGeneric<Usuario>>(response,HttpStatus.CREATED);
		}
	}
	
	@GetMapping("/verification/{idUsuario}")
	public ResponseEntity<String> verificarCuenta(@PathVariable Long idUsuario){
		ResponseEntity<String> response;
		Usuario usuario = usuarioRepository.findUsuarioVerification(idUsuario, ConstantesNegocio.ESTADO_USUARIO_REGISTRADO);
		if(usuario != null) {
			EstadoUsuario estado = new EstadoUsuario();
			estado.setIdEstado(ConstantesNegocio.ESTADO_USUARIO_VERIFICADO);
			usuario.setEstadoUsuario(estado);
			usuarioRepository.save(usuario);
			
			try {
				//Envío de Email
				sendmail(usuario.getLogin().getEmail(), ConstantesNegocio.EMAIL_VALIDAR_USUARIO, 
						ConstantesNegocio.MSG_VERIFICACION_CUENTA);
			} catch (MessagingException | IOException e) {
				e.printStackTrace();
			}
			
			response = new ResponseEntity<>(ConstantesNegocio.MSG_VERIFICACION_CUENTA, HttpStatus.OK);
		}else {
			response = new ResponseEntity<>(ConstantesNegocio.MSG_VERIFICATION_ERROR, HttpStatus.OK);
		}
		return response;
	}
	
	@GetMapping("/usuarios/{idUsuario}")
	public ResponseEntity<ResponseGeneric<Usuario>> consultarUsuario(@PathVariable Long idUsuario) {
		ResponseGeneric<Usuario> response =  new ResponseGeneric<Usuario>();
		Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);
		if(usuario != null) {
			usuario.setLogin(loginRepository.findByIdUsuario(idUsuario));
			response.setObject(usuario);
			return new ResponseEntity<ResponseGeneric<Usuario>>(response,HttpStatus.OK);
		} else {
			response.setMsg(ConstantesNegocio.NOT_FOUND);
			return new ResponseEntity<ResponseGeneric<Usuario>>(response,HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/usuarios/{idUsuario}")
	public ResponseEntity<ResponseGeneric<Usuario>> eliminarUsuario(@PathVariable Long idUsuario) {
		ResponseGeneric<Usuario> response =  new ResponseGeneric<Usuario>();
		Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);
		if(usuario != null) {
			usuarioRepository.delete(usuario);
			response.setMsg(ConstantesNegocio.MSG_USUARIO_DELETE);
			return new ResponseEntity<ResponseGeneric<Usuario>>(response,HttpStatus.OK);
		}
		else {
			response.setMsg(ConstantesNegocio.NOT_FOUND);
			return new ResponseEntity<ResponseGeneric<Usuario>>(response,HttpStatus.NOT_FOUND);
		}	
	}
	
	@PutMapping("/usuarios/")
	public ResponseEntity<ResponseGeneric<Usuario>> editarUsuario(@Valid @RequestBody Usuario updatedUser){
		ResponseGeneric<Usuario> response =  new ResponseGeneric<Usuario>();
		
		Usuario usuario = usuarioRepository.findByIdUsuario(updatedUser.getIdUsuario());
		if(usuario != null) {
			//Actualizando Datos Usuario
			Long idUsuario = usuarioRepository.updateUsuario(
					updatedUser.getIdUsuario(), updatedUser.getNombre(), 
					updatedUser.getApellidoPaterno(), updatedUser.getApellidoMaterno(),
					updatedUser.getGenero());
			
			//Actualizando Datos Login
			loginRepository.updateLogin(updatedUser.getLogin().getEmail(), 
					updatedUser.getLogin().getPassword(), 
					idUsuario);
			
			response.setMsg(ConstantesNegocio.MSG_USUARIO_EDIT);
			response.setObject(updatedUser);
			return new ResponseEntity<ResponseGeneric<Usuario>>(response,HttpStatus.OK);
		} else {
			response.setMsg(ConstantesNegocio.NOT_FOUND);
			return new ResponseEntity<ResponseGeneric<Usuario>>(response,HttpStatus.NOT_FOUND); 
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseGeneric<Usuario>> postLogin(@RequestBody Login login){		
		ResponseGeneric<Usuario> response =  new ResponseGeneric<Usuario>();
		Login loginValido = loginRepository.findByEmailAndPassword(login.getEmail(), login.getPassword());		
		if(loginValido == null) {
			response.setMsg(ConstantesNegocio.MSG_LOGIN_ERROR);
			return new ResponseEntity<ResponseGeneric<Usuario>>(response,HttpStatus.UNAUTHORIZED);
		} else {
			response.setMsg(ConstantesNegocio.MSG_LOGIN_SUCCESS + " "+loginValido.getUsuario().getNombre());
			response.setObject(loginValido.getUsuario());
			return new ResponseEntity<ResponseGeneric<Usuario>>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@GetMapping("/password/{email}")
	public ResponseEntity<String> reestablecerPassword(@PathVariable String email){
		ResponseEntity<String> response;
		Login login = loginRepository.findByEmail(email);
		if(login != null) {
			try {
				Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
				String url = "172.20.10.6";
				
                for (; n.hasMoreElements();){
                	NetworkInterface e = n.nextElement();
                    Enumeration<InetAddress> a = e.getInetAddresses();
                    if(e.getName().equals("wlp7s0")) {
                    	int aux = 1;
                    	for (; a.hasMoreElements();){
                    		InetAddress addr = a.nextElement();
                    		if(aux == 2) {
                    			
                    			url = "http://"+addr.getHostAddress()+":"
                						+env.getProperty("local.server.port")
                						+"/password/new/"+login.getUsuario().getIdUsuario();
                    			break;
                    		}
                        	aux++;
                        }
                    }
                }
				sendmail(email, ConstantesNegocio.EMAIL_RECUPERAR_PASSWORD, 
						login.getUsuario().getNombre()+" da click en el link: "+url);
				
				response = new ResponseEntity<String>(ConstantesNegocio.MSG_PASSWORD_EMAIL, HttpStatus.OK);
			} catch (MessagingException | IOException e) {
				e.printStackTrace();
				response = new ResponseEntity<String>(ConstantesNegocio.MSG_ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else{
			response = new ResponseEntity<String>(ConstantesNegocio.MSG_EMAIL_ERROR, HttpStatus.OK);
		}
		return response;
	}
	
	@GetMapping("/password/new/{idUsuario}")
	public void generarPassword(@PathVariable Long idUsuario){
		
		Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);
		if(usuario != null) {			
			Login login = usuario.getLogin();
			String newPassword = generatePassword();
			login.setPassword(newPassword);
			loginRepository.save(login);
			
			try {
				sendmail(login.getEmail(), ConstantesNegocio.EMAIL_RECUPERAR_PASSWORD, 
						login.getUsuario().getNombre()+" tu nueva contraseña es: "+newPassword);
			} catch (MessagingException | IOException e) {
				e.printStackTrace();
			}
		} else { 
			System.out.println("Usuario incorrecto");
		}
		
	}
	
	private void sendmail(String email, String subject, String contenido) throws AddressException, MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		  
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("gasolimetro@gmail.com", "qiBkuw-1gebmy-foqfef");
		    }
		});
		   
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("gasolimetro@gmail.com", false));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
		msg.setSubject(subject);
		msg.setContent(contenido, "text/html");
		msg.setSentDate(new Date());
		   
		Transport.send(msg);   
	}
	
	private String generatePassword() {
		StringBuilder returnValue = new StringBuilder(10);
        for (int i = 0; i < 10 ; i++) {
            returnValue.append(ConstantesNegocio.ALPHABET.charAt(
            		RANDOM.nextInt(ConstantesNegocio.ALPHABET.length())));
        }
        return new String(returnValue);
	}
	
}
