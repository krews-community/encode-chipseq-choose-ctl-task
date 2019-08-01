package step
import java.nio.file.*
import util.CmdRunner
fun CmdRunner.choose_ctl(taFiles:List<Path>,ctaFiles:List<Path>,ctlpooledtaFile:Path,ctlDepthRatio:Double,alwaysUsePooledCtl:Boolean, outputPrefix:List<String>,outDir: Path) {
    Files.createDirectories(outDir)
    //log.info('Choosing appropriate control for each IP replicate...')
    var array = IntArray(taFiles.size) { it -> 0 }
    if(alwaysUsePooledCtl)
    {
        array = IntArray(taFiles.size) { it -> -1 }
    }else
    {
        val nlines : MutableList<Int> = ArrayList()
        val nlines_ctl : MutableList<Int> = ArrayList()
        taFiles.forEach { it->
            val l = this.runCommand("zcat ${it} | wc -l" )!!.trim().toInt()
            nlines.add(l)        }
        ctaFiles.forEach { it->
            val l = this.runCommand("zcat ${it} | wc -l" )!!.trim().toInt()
            nlines_ctl.add(l)        }
        var use_pooled_ctl = false
        for (i in 0..nlines_ctl.size-1) {
            for (j in 0..nlines_ctl.size-1) {
                if(i!==j && nlines_ctl[i].toFloat()/nlines_ctl[j].toFloat() > ctlDepthRatio || nlines_ctl[j].toFloat()/nlines_ctl[i].toFloat()  > ctlDepthRatio)
                {
                    use_pooled_ctl = true
                    break;
                }
            }
        }
        //  log.info('Writing ctl_for_repN.tagAlign.gz files...')
        if(use_pooled_ctl)
        {
            array = IntArray(taFiles.size) { it -> -1 }
        }else {
            for (i in 0..taFiles.size-1) {
                if(i> ctaFiles.size -1 )
                {
                    array.set(i,-1)
                }
                else if(nlines[i] >  nlines_ctl[i])
                {
                    array.set(i,-1)
                }else {
                    array.set(i,i)
                }
            }
        }

}
    for (i in array.indices) {
        var rep_id = outputPrefix[i]
        var src:Path
        val dest = outDir.resolve( "${rep_id}.tagAlign.gz")
        if(array[i]===-1)
        {
            src = ctlpooledtaFile
        }else {
            src = ctaFiles[array[i]]
        }
        this.run("cp -f ${src} ${dest}")
    }
}