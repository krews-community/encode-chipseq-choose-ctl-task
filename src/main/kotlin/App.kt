import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.*
import step.*
import util.*
import java.nio.file.*
import util.CmdRunner
fun main(args: Array<String>) = Cli().main(args)class Cli : CliktCommand() {
    private val outputPrefix: List<String> by option("-outputPrefix", help = "output file name prefix; defaults to 'output'").multiple().validate { require(it.isNotEmpty()) {"At least output prefix must be given"} }
    private val outDir by option("-outputDir", help = "path to output Directory")
            .path().required()
    private val taFiles: List<Path> by option("-taFiles", help = "path for TAGALIGN file ")
            .path().multiple().validate { require(it.isNotEmpty()) {"At least one path must be given"} }
    private val ctaFiles: List<Path> by option("-ctaFiles", help = "path for control TAGALIGN file ")
            .path().multiple().validate { require(it.isNotEmpty()) {"At least one path must be given"} }

    private val ctlpooledtaFile: Path by option("-ctlpooledta", help = "path for pooled TAGALIGN file ")
            .path().required()
    private val ctlDepthRatio: Double by option("-ctl-depth-ratio", help = "Control depth ratio.").double().required()
    private val alwaysUsePooledCtl: Boolean by option("-always-use-pooled-ctl", help = "Always use pooled control for all IP replicates.").flag()
    override fun run() {
        val cmdRunner = DefaultCmdRunner()
        cmdRunner.runTask(taFiles,ctaFiles,ctlpooledtaFile,ctlDepthRatio,alwaysUsePooledCtl,outputPrefix,outDir)
    }
}/**
 * Runs pre-processing and bwa for raw input files
 */
fun CmdRunner.runTask(taFiles:List<Path>,ctaFiles:List<Path>,ctlpooledtaFile:Path,ctlDepthRatio:Double,alwaysUsePooledCtl:Boolean,
                      outputPrefix:List<String>,outDir: Path) {
    choose_ctl(taFiles,ctaFiles,ctlpooledtaFile,ctlDepthRatio,alwaysUsePooledCtl,outputPrefix,outDir)
}