package GerarPlanilha;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import cadastro.Item;
import cadastro.ItemDAO;

public class GerarPlanilhaDeItens {

	private static String[] columns = { "Tipo", "Nome", "Und. Medida", "Preço", "Descrição" };
	private static List<Item> todosOsItens = new ArrayList<>();
	private ItemDAO itemDAO = new ItemDAO();

	public void recuperarItens() {
		try {
			todosOsItens = itemDAO.recuperarTodosOsItens();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gerarRelatorio() throws IOException, InvalidFormatException {

		Workbook workbook = new SXSSFWorkbook();
		// new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Itens cadastrados");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 1;
		for (Item item : todosOsItens) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(item.getTipoItem());

			row.createCell(1).setCellValue(item.getNome());

			row.createCell(2).setCellValue(item.getUnidadeDeMedida());
			;

			row.createCell(3).setCellValue(item.getPreco());

			row.createCell(4).setCellValue(item.getDescrcao());
		}

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("todos-itens-cadastrados.xlsx");
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();

	}

	public static void main(String[] args) {

		GerarPlanilhaDeItens g = new GerarPlanilhaDeItens();
		g.recuperarItens();
		try {
			g.gerarRelatorio();
		} catch (InvalidFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
