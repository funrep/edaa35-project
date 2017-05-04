source("EDAA35/R_resources.R")

compile <- function() {
    system("scalac ../Quicksort.scala ../Fibonacci.scala ../Primes.scala ../Main.scala")
}

toPdf <- function(data, outfile) {
    pdf(outfile)
    plot(data, type='l')
    dev.off()
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


main <- function() {
    data_quick_imp <- runImp("quicksort", "../data/random.txt", "quick", start=0)
    data_quick_fun <- runFun("quicksort", "../data/random.txt", "quick", start=0)
    toPdf(data_quick_imp, "../results/invsägning-imp.pdf")
    toPdf(data_quick_fun, "../results/insvägning-fun.pdf")

    print("fibonacci")
    fib_imp <- analyze("fibonacci", "10000", "imp")
    print(fib_imp[1:3])
    fib_fun <- analyze("fibonacci", "10000", "fun")
    print(fib_imp[1:3])

    print("primes")
    primes_imp <- analyze("primes", "1000", "imp")
    print(primes_imp[1:3])
    primes_fun <- analyze("primes", "1000", "fun")
    print(primes_fun[1:3])

    print("quicksort")
    quick_imp <- analyze("quicksort", "../data/random.txt", "imp")
    print(quick_imp[1:3])
    quick_fun <- analyze("quicksort", "../data/random.txt", "fun")
    print(quick_fun[1:3])
}
