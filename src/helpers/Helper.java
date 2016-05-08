package helpers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.text.MaskFormatter;

public class Helper {
	public static final int DEFAULT_FRAME_HEIGTH = 340;
	public static final int DEFAULT_FRAME_WIDTH = 620;
	
	public static void centralizarJanela(JFrame janela) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		janela.setLocation(dim.width/2 - janela.getSize().width/2, dim.height/2-janela.getSize().height/2);
	}
	
	public static String aplicarMascaraTelefone(Long telefone) throws java.text.ParseException {
		MaskFormatter maskFormat = new MaskFormatter("(##)####-####");
		maskFormat.setValueContainsLiteralCharacters(false);
		
		return maskFormat.valueToString(telefone);
	}
	
	public final static String DATE_FORMAT = "dd/MM/yyyy";
	public static boolean validarData(String date) {
	        try {
	            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	            df.setLenient(false);
	            df.parse(date);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	}
}
