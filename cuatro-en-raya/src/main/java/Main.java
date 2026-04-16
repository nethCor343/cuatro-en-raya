import com.nethcorp.cuatroenraya.model.*;
import com.nethcorp.cuatroenraya.view.*;
import com.nethcorp.cuatroenraya.controller.*;

public class Main {
    public static void main(String[] args) {

        int filas = 6;
        int columnas = 7;

        Partida modelo = new Partida(filas, columnas);
        
        VistaTexto vista = new VistaTexto();
        

        ControladorJuego controlador = new ControladorJuego(modelo, vista);
        controlador.iniciarPartida();
    }
    
}
