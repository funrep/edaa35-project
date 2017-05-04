source("EDAA35/R_resources.R")

compile <- function() {
    system("scalac ../Quicksort.scala ../Fibonacci.scala ../Primes.scala ../Main.scala")
}

N <- 600

runImp <- function(algo, input, outfile, start=100) {
    system(paste("scala", "Main", algo, input, outfile, N))
    impData <- read.csv(paste0(outfile, "-imp.csv"))
    impData[start:nrow(impData),]
    impData
}

runFun <- function(algo, input, outfile, start=100) {
    system(paste("scala", "Main", algo, input, outfile, N))
    funData <- read.csv(paste0(outfile, "-fun.csv"))
    funData[start:nrow(funData),]
    funData
}

analyze <- function(algo, input, paradigm) {
    data <- c()
    times <- 10
    for (i in 1:times) {
        if (paradigm == "fun") {
            result <- runFun(algo, input, algo)
            data <- c(data, mean(result[,2]))
        } else {
            result <- runImp(algo, input, algo)
            data <- c(data, mean(result[,2]))
        }
    }
    mean0 <- mean(data)
    confidence <- confidenceInterval(data)
    c(mean0, confidence)
}

