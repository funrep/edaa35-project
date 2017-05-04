object Quicksort {
  def imperative(xs: Array[Int]) {
    def swap(i: Int, j: Int) {
        val t = xs(i); xs(i) = xs(j); xs(j) = t
    }

    def sort(l: Int, r: Int) {
        val pivot = xs((l + r) / 2)
        var i = l; var j = r
        while (i <= j) {
            while (xs(i) < pivot) i += 1
            while (xs(j) > pivot) j -= 1
            if (i <= j) {
                swap(i, j)
                i += 1
                j -= 1
            }
        }
        if (l < j) sort(l, j)
        if (j < r) sort(i, r)
    }

    sort(0, xs.length - 1)
}
  def functional(xs: Array[Int]): Array[Int] = {
    if (xs.length <= 1) xs
    else {
        val pivot = xs(xs.length / 2)
        Array.concat(
            functional(xs filter (pivot >)), 
            (xs filter (pivot ==)), 
            functional(xs filter (pivot <)))
    }
}
}