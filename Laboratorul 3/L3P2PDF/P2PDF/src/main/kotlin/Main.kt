import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFRun
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

fun main() {
    val inputFilePath = "Miss-Peregrine-C-minul-copiilor-deosebi-i-de-Ransom-Riggs-pdf.docx"
    val outputFilePath = "output.docx"

    try {
        val inputStream = FileInputStream(File(inputFilePath))
        val document = XWPFDocument(inputStream)

        for (paragraph in document.paragraphs) {
            var text = paragraph.text

            text = text.replace("Ş", "Ș")
            text = text.replace("Ţ", "Ț")
            text = text.replace("ş", "ș")
            text = text.replace("ţ", "ț")
            text = text.replace("Ă", "ă")
            text = text.replace("Â", "â")
            text = text.replace("Î", "î")
            text = text.replace("Ș", "Ș")
            text = text.replace("Ț", "Ț")

            text = text.replace(Regex("[^\\x20-\\x7E]"), "")
            text = text.replace(Regex("\\s+"), " ")
            text = text.trim()

            println(text)

            val runsToRemove = ArrayList<XWPFRun>(paragraph.runs)
            for (run in runsToRemove) {
                paragraph.removeRun(paragraph.runs.indexOf(run))
            }

            val newRun: XWPFRun = paragraph.createRun()
            newRun.setText(text)
        }

        val outputStream = FileOutputStream(outputFilePath)
        document.write(outputStream)

        inputStream.close()
        outputStream.close()

        println("Fișierul a fost procesat și salvat în $outputFilePath")
    } catch (e: IOException) {
        println("Eroare la procesarea fișierului: ${e.message}")
    }
}
