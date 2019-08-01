package testutil
import java.nio.file.*

fun getResourcePath(relativePath: String): Path {

    val url = TestCmdRunner::class.java.classLoader.getResource(relativePath)
     return Paths.get(url.toURI())
}

// Resource Directories
val testInputResourcesDir = getResourcePath("test-input-files")
val testOutputResourcesDir = getResourcePath("test-output-files")


// Test Working Directories
val testDir = Paths.get("/tmp/chipseq-test")!!
val testInputDir = testDir.resolve("input")!!
val testOutputDir = testDir.resolve("output")!!


val TA2 = testInputDir.resolve("ENCFF000ASP.tagAlign.gz")
val TA1 = testInputDir.resolve("ENCFF000ASU.tagAlign.gz")
val CTA1 = testInputDir.resolve("ENCFF000ASU_ENCFF000ARK.tagAlign.gz")
val CTA2 = testInputDir.resolve("ENCFF000ASP_ENCFF000ARO.tagAlign.gz")

val POOLED_CTL = testInputDir.resolve("pooled_ta_ctl.pooled.tagAlign.gz")
