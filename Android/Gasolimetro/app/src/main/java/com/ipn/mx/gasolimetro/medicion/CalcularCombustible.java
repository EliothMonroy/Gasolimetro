package com.ipn.mx.gasolimetro.medicion;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ipn.mx.gasolimetro.R;
import com.ipn.mx.gasolimetro.datos.modelos.UsuarioLogin;

public class CalcularCombustible extends DialogFragment {

    private EditText cantidadLitros;
    private static String  litrosCargados;

    public CalcularCombustible(){
    }

    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public static CalcularCombustible newInstance(String title) {
        CalcularCombustible frag = new CalcularCombustible();
        Bundle args = new Bundle();
        args.putString("Litros", title);
        litrosCargados = title;
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_calcular_combustible, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        cantidadLitros = view.findViewById(R.id.txt_Litros);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("Litros", "Cantidad de litros");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        view.findViewById(R.id.buttonContinuar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return input text back to activity through the implemented listener
                try {
                    CalcularCombustible.EditNameDialogListener listener = (CalcularCombustible.EditNameDialogListener) getActivity();
                    listener.onFinishEditDialog(cantidadLitros.getText().toString());
                    String cantidadIntroducida = cantidadLitros.getText().toString();

                    Bundle bundle= getArguments();
                    bundle.putString("litrosSolicitados",cantidadIntroducida);
                    UsuarioLogin usuarioLogin=bundle.getParcelable("UsuarioLogin");

                    Log.d("UsuarioLogin", "---> "+usuarioLogin.getApellidoPaterno());
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    ConfirmarMedicion confirmarMedicion = ConfirmarMedicion.newInstance(litrosCargados);
                    confirmarMedicion.setArguments(bundle);
                    confirmarMedicion.show(fm, "fragment_confirmar_medicion");
                    Log.d("valor", "---> Cantidad Introducida");
                }catch (Exception e){
                    Log.d("errorCalculo", "No introduciste nada");
                }finally {
                    dismissAllowingStateLoss();
                }
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle bundle){

    }

}
