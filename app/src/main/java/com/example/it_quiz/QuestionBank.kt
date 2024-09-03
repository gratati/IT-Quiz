package com.example.it_quiz


class QuestionBank {

    fun getQuestions(): List<Question> {
        return listOf(
            Question("Полная форма URL-адреса такова?", "Uniform Resource Locator", "Unified Resource Link", "Universal Resource List", "Uniform Resource Link", "A"),
            Question("Как расшифровывается аббревиатура HTML?", "HyperText Markdown Language", "HyperText Markup Language", "HyperTool Multiple Language", "Hyperlink Text Markup Language", "B"),
            Question("Какой протокол используется для защиты информации при передаче?", "HTTP", "FTP", "SSH", "SSL", "D"),
            Question("Какая компания разработала язык программирования Java?", "Microsoft", "Apple", "Sun Microsystems", "Google", "C"),
            Question("Что такое IP-адрес?", "Имя компьютера", "Уникальный идентификатор сети", "Уникальный идентификатор устройства", "Интернет-протокол", "C"),
            Question("Как называется процесс увеличения масштаба приложения?", "Multi-threading", "Scaling", "Parallelism", "Concurrency", "B"),
            Question("Какой язык используется для стилей веб-страниц?", "HTML", "JavaScript", "Python", "CSS", "D"),
            Question("Кто является создателем операционной системы Linux?", "Bill Gates", "Linus Torvalds", "Steve Jobs", "Dennis Ritchie", "B"),
            Question("Какое расширение файла используется для сохранения веб-страницы?", ".web", ".txt", ".html", ".css", "C"),
            Question("Что такое SQL?", "Structured Query Language", "Simple Query Language", "System Query Language", "Syntax Query Language", "A")
        )
    }
}