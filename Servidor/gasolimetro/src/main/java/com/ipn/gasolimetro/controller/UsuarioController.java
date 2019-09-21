package com.ipn.gasolimetro.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ipn.gasolimetro.entity.Login;
import com.ipn.gasolimetro.entity.Usuario;
import com.ipn.gasolimetro.repository.LoginRepository;
import com.ipn.gasolimetro.repository.UsuarioRepository;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@GetMapping("/usuarios")
	public List<Usuario> getUsuarios(){
		return usuarioRepository.findAll();
	}
	
	@GetMapping("/usuarios/{idUsuario}")
	public Resource<Usuario> getUsuario(@PathVariable Long idUsuario) {
		Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
		if(!usuario.isPresent())
			System.out.println("User Not Found");
		
		Resource<Usuario> resource = new Resource<Usuario>(usuario.get());
		//Implementar HATEOAS
		return resource;
	}
	
	@DeleteMapping("/usuarios/{idUsuario}")
	public void deleteUsuario(@PathVariable Long idUsuario) {
		Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
		if(!usuario.isPresent())
			System.out.println("Exception user not found");
		else
			usuarioRepository.delete(usuario.get());
	}

	@PostMapping("/usuarios")
	public ResponseEntity<Object> postUsuario(@Valid @RequestBody Usuario user){		
		Usuario usuario = usuarioRepository.save(user);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(usuario.getIdUsuario())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/usuarios/{idUsuario}")
	public ResponseEntity<Object> putUsuario(@Valid @RequestBody Usuario updatedUser, @PathVariable Long idUsuario){
		Usuario usuario = new Usuario(); 
		Optional<Usuario> usuarioValido = usuarioRepository.findById(idUsuario);
		if(!usuarioValido.isPresent())
			System.out.println("Exception user not found");
		else{
			updatedUser.setIdUsuario(usuarioValido.get().getIdUsuario());
			usuario = usuarioRepository.save(updatedUser);
		}
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("")
				.buildAndExpand(usuario.getIdUsuario())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/login")
	public Resource<Usuario> postLogin(@RequestBody Login login){
		
		Optional<Login> loginValido = loginRepository.findByEmailAndPassword(login.getEmail(), login.getPassword());
		
		if(!loginValido.isPresent())
			System.out.println("Exception user not found");
		
		Resource<Usuario> resource = new Resource<Usuario>(loginValido.get().getUsuario());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUsuario(loginValido.get().getUsuario().getIdUsuario()));
		resource.add(linkTo.withRel("usuarioInfo"));
		
		return resource;
	}
	
}
