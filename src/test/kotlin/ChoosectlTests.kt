import org.junit.jupiter.api.*
import step.*
import testutil.*
import testutil.cmdRunner
import testutil.setupTest
import java.nio.file.*
import org.assertj.core.api.Assertions.*

class ChoosectlTests {
    @BeforeEach fun setup() = setupTest()
    @AfterEach fun cleanup() = cleanupTest()

     @Test fun `run choose ctl `() {
         var taFiles = mutableListOf<Path>()
         taFiles.add(TA1)
         taFiles.add(TA2)
         var ctaFiles = mutableListOf<Path>()
         ctaFiles.add(CTA1)
         ctaFiles.add(CTA2)
         cmdRunner.choose_ctl(taFiles,ctaFiles,POOLED_CTL,1.2,false, listOf("ctl1","ctl2"),testOutputDir)
         assertThat(testOutputDir.resolve("ctl1.tagAlign.gz")).exists()
         assertThat(testOutputDir.resolve("ctl2.tagAlign.gz")).exists()
    }

}