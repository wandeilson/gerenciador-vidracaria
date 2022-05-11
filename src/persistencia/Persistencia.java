package persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class Persistencia {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private String nomeArquivoASerSalvo;

	private File arquivo;
	
	

	
	public Persistencia(String nomeArquivoASerSalvo) {
		xstream.addPermission(AnyTypePermission.ANY);
		arquivo = new File(nomeArquivoASerSalvo);
	}
	

	public void salvarArquivo(Object tipoArquivoASerSalvo) {

		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n";
		xml += xstream.toXML(tipoArquivoASerSalvo);
		try {
			arquivo.createNewFile();

			PrintWriter gravar = new PrintWriter(arquivo);
			gravar.print(xml);
			gravar.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Object recuperarArquivo(String nomeArquivoASerRecuperado) {
		File file = new File(nomeArquivoASerRecuperado);
		try {
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				return (Object) xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new Object();
	}
}
