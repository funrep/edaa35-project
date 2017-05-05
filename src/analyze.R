this.dir <- dirname(parent.frame(2)$ofile)
setwd(this.dir)

source("R_resources.R", local = TRUE)

compile <- function() {

    system("scalac Main.scala")
}

toPdf <- function(data, outfile) {
    pdf(outfile)
    plot(data, type='l')
    dev.off()
}


runImp <- function(algo, input, outfile, start, N) {
    system(paste("scala", "Main", algo, input, outfile, N))
    impData <- read.csv(paste0(outfile, "-imp.csv"))
    impData[start:nrow(impData),]
    impData
}

runFun <- function(algo, input, outfile, start, N) {
    system(paste("scala", "Main", algo, input, outfile, N))
    funData <- read.csv(paste0(outfile, "-fun.csv"))
    funData[start:nrow(funData),]
    funData
}

analyze <- function(algo, input, paradigm, s, n) {
    data <- c()
    times <- 10
    for (i in 1:times) {
        if (paradigm == "fun") {
            result <- runFun(algo, input, algo, s, n)
            data <- c(data, mean(result[,2]))
        } else {
            result <- runImp(algo, input, algo, s, n)
            data <- c(data, mean(result[,2]))
        }
    }
    mean0 <- mean(data)
    confidence <- confidenceInterval(data)
    c(mean0, confidence)
}


main <- function() {

    print("fibonacci")
    fib_imp <- analyze("fibonacci", "10000", "imp", 100, 600)
    print(fib_imp[1:3])
    fib_fun <- analyze("fibonacci", "10000", "fun", 100, 600)
    print(fib_fun[1:3])

    print("primes")
    primes_imp <- analyze("primes", "1000", "imp", 100, 600)
    print(primes_imp[1:3])
    primes_fun <- analyze("primes", "1000", "fun", 150, 600)
    print(primes_fun[1:3])

    print("quicksort")
    quick_imp <- analyze("quicksort", "../data/random.txt", "imp", 150, 600)
    print(quick_imp[1:3])
    quick_fun <- analyze("quicksort", "../data/random.txt", "fun", 100, 600)
    print(quick_fun[1:3])
}
