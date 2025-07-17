debug <- FALSE

file.move <- function(from, to, overwrite = TRUE) {
  if (file.copy(from, to, overwrite))
    unlink(from)
}

rmarkdown::render("README.Rmd", rmarkdown::github_document())

if (debug) {
  file.move("README.html", "../README.html")
} else {
  unlink("README.html")
}

file.move("README.md", "../README.md")
