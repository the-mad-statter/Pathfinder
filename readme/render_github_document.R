file.move <- function(from, to, overwrite = TRUE) {
  if (file.copy(from, to, overwrite))
    unlink(from)
}

rmarkdown::render("README.Rmd", rmarkdown::github_document())
unlink("README.html")
file.move("README.md", "../README.md")