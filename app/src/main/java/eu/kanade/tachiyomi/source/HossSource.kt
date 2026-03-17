package eu.kanade.tachiyomi.source

import org.jsoup.nodes.Document
import eu.kanade.tachiyomi.source.model.Page
import eu.kanade.tachiyomi.source.online.HttpSource
import eu.kanade.tachiyomi.source.model.SChapter
import eu.kanade.tachiyomi.source.model.SManga

class HossSource : HttpSource() {
    override val id: Long = 1234567890L
    override val name = "Hoss Manhwa"
    override val baseUrl = "https://hossmanhwa.com/"
    override val lang = "pt-BR"
    override val supportsLatest = true

    // Essas funções abaixo são obrigatórias para o app não dar erro ao abrir
    override fun popularMangaRequest(page: Int) = GET("$baseUrl/manga-list/", headers)
    override fun latestUpdatesRequest(page: Int) = GET(baseUrl, headers)
    override fun searchMangaRequest(page: Int, query: String, filters: FilterList) = GET("$baseUrl/?s=$query", headers)
    
    // O seu código original de extrair as páginas (perfeito!)
    fun extrairPaginas(document: Document): List<Page> {
        return document.select(".reading-content img").mapIndexed { index, element ->
            val urlImagem = element.attr("abs:data-src").ifEmpty { element.attr("abs:src") }
            Page(index, "", urlImagem)
        }
    }

    // Funções vazias apenas para o código compilar (ficar verde)
    override fun popularMangaParse(response: Response): MangasPage = MangasPage(emptyList(), false)
    override fun latestUpdatesParse(response: Response): MangasPage = MangasPage(emptyList(), false)
    override fun searchMangaParse(response: Response): MangasPage = MangasPage(emptyList(), false)
    override fun mangaDetailsParse(document: Document): SManga = SManga.create()
    override fun chapterListParse(response: Response): List<SChapter> = emptyList()
    override fun pageListParse(response: Response): List<Page> = emptyList()
    override fun imageUrlParse(response: Response): String = ""
}