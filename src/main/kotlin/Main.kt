import java.io.File

fun main() {
    maxFiles()
    maxDepth()
    emptyDirs()
}

private fun maxFiles() {
    val file = File("D:\\Projects\\JetBrainsAcademy\\FileHierarchy\\basedir")
    val walk = file.walkBottomUp()
    var max = 0
    var counter = 0
    var dir = ""
    walk.forEach {
        if (it.isDirectory) {
            if (counter > max) {
                max = counter
                dir = it.name
            }
            counter = 0
        } else counter++
    }

    println("$dir $max")
}

private fun maxDepth() {
    val (max, fileName) = howDeep(File("D:\\Projects\\JetBrainsAcademy\\FileHierarchy\\basedir2"))

    println("$max $fileName")
}

fun howDeep(file: File): Pair<Int, String> {
    var maxDepth = 0
    var deepestFileName = file.name
    if (file.isDirectory)
        file.listFiles()?.forEach { subFile ->
            with(howDeep(subFile)) { depth: Int, fileName: String ->
                if (depth + 1 > maxDepth) {
                    maxDepth = depth + 1
                    deepestFileName = fileName
                }
            }
        }

    return maxDepth to deepestFileName
}

fun <F, S, R> with(receiver: Pair<F, S>, block: Pair<F, S>.(F, S) -> R) =
    receiver.block(receiver.first, receiver.second)

fun emptyDirs() {
    val dirList = mutableListOf<String>()
    findEmpty(File("D:\\Projects\\JetBrainsAcademy\\FileHierarchy\\basedir3"), dirList)

    println(dirList.joinToString(" "))
}

fun findEmpty(file: File, dirList: MutableList<String>) {
    if (file.isDirectory)
        file.listFiles()?.let { fileList ->
            if (fileList.isEmpty())
                dirList.add(file.name)
            else
                fileList.forEach { subFile ->
                    findEmpty(subFile, dirList)
                }
        }
}
