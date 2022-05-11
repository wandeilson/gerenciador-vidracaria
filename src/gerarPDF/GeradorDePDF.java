package gerarPDF;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;

import cadastro.Orcamento;

public class GeradorDePDF {

	public boolean gerarRelatorio(Orcamento orcamento) {
		Document documento = new Document(PageSize.A4, 72,72,72,72);
		try {
			PdfWriter.getInstance(documento, new FileOutputStream(orcamento.getNomeDoOrcamento()+".pdf"));
			documento.open();
			Font f = new Font(FontFamily.COURIER, 12, Font.BOLD);

			String orcamentoStr = orcamento.toString();
			Paragraph p = new Paragraph(orcamentoStr, f);
			documento.add(p);
			documento.close();
			return true;
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	
}
