import org.junit.*
import org.junit.jupiter.api.*
import com.lesfurets.jenkins.unit.*
import com.lesfurets.jenkins.unit.declarative.*
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import static com.lesfurets.jenkins.unit.global.lib.ProjectSource.projectSource
import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library

class myPipelineTest extends DeclarativePipelineTest {
  @Before
  @Override
  void setUp() throws Exception {
    super.setUp()
    helper.registerAllowedMethod('withAWS', [Map.class,Closure.class], null)
    helper.registerAllowedMethod('sh', [Map.class], null)
    def library = library()
                  .name('myLib')
                  .defaultVersion("local")
                  .allowOverride(true)
                  .implicit(false)
                  .targetPath("local")
                  .retriever(projectSource())
                  .build()
    helper.registerSharedLibrary(library)
    binding.setVariable("env",
      [
        GIT_BRANCH: "master",
        WORKSPACE: new File('.').absolutePath + "/resources",
      ]
    )
  }

  @Test
  void "should run"() throws Exception {
    runScript("resources/Jenkinsfile")
    printCallStack()
    assertJobStatusSuccess()
  }
}
