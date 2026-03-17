package eu.kanade.tachiyomi.source

import eu.kanade.tachiyomi.source.model.Page
import eu.kanade.tachiyomi.source.model.SChapter
import eu.kanade.tachiyomi.source.model.SManga
import eu.kanade.tachiyomi.source.model.MangasPage
import eu.kanade.tachiyomi.source.online.HttpSource
import okhttp3.Request
import okhttp3.Response
import org.jsoup.nodes.Document

class HossSource : HttpSource() {
    override val id: Long = 1234567890L
    override val name = "Hoss Manhwa"
    override val baseUrl = "https://hossmanhwa.com"
    override val lang = "pt-BR"
    override val supportsLatest = true

    // Funções obrigatórias para o motor do app aceitar o arquivo
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/manga-list/", headers)
    override fun popularMangaParse(response: Response): MangasPage = MangasPage(emptyList(), false)
    override fun latestUpdatesRequest(page: Int): Request = GET(baseUrl, headers)
    override fun latestUpdatesParse(response: Response): MangasPage = MangasPage(emptyList(), false)
    override fun searchMangaRequest(page: Int, query: String, filters: FilterList): Request = GET("$baseUrl/?s=$query", headers)
    override fun searchMangaParse(response: Response): MangasPage = MangasPage(emptyList(), false)
    override fun mangaDetailsParse(response: Response): SManga = SManga.create()
    override fun chapterListParse(response: Response): List<SChapter> = emptyList()
    override fun pageListParse(response: Response): List<Page> = emptyList()
    override fun imageUrlParse(response: Response): String = ""

    // Sua lógica de extração
    fun extrairPaginas(document: Document): List<Page> {
        return document.select(".reading-content img").mapIndexed { index, element ->
            val urlImagem = element.attr("abs:data-src").ifEmpty { element.attr("abs:src") }
            Page(index, "", urlImagem)
        }
    }
}