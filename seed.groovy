PACKAGES = [
    'demo-package1',
    'demo-package2',
]

VIEWS = [[
    name: 'CENTOS6',
]]

ROOT = "EL/${BRANCH_NAME}"

folder(ROOT) {
}

for (view in VIEWS) {
    listView("${ROOT}/${view.name}") {
        recurse()
    }
}

for (jobName in PACKAGES) {
    multibranchPipelineJob("${ROOT}/${jobName}") {
        branchSources {
            branchSource {
                source {
                    git {
                        remote("https://github.com/haney/${jobName}.git")
                    }
                }
            }
        }
        factory {
            remoteJenkinsFileWorkflowBranchProjectFactory {
                // Relative location within the checkout of your Pipeline script.
                remoteJenkinsFile("package.jenkinsfile")
                localMarker('')
                remoteJenkinsFileSCM {
                    // The git plugin provides fundamental git operations for Jenkins projects.
                    gitSCM {
                        // Specify the repository to track.
                        userRemoteConfigs {
                            userRemoteConfig {
                                // Specify the URL or path of the git repository.
                                url('https://github.com/haney/demo-jobdsl.git')
                                credentialsId('github-p4components-app')
                                // ID of the repository, such as origin, to uniquely identify this repository among other remote repositories.
                                name("origin")
                                // A refspec controls the remote refs to be retrieved and how they map to local refs.
                                refspec("+refs/heads/${BRANCH_NAME}:refs/remotes/origin/${BRANCH_NAME}")
                            }
                        }
                        // matchBranches(false)
                        // fallbackBranch("${scmBranch}")
                        // lookupInParameters(false)
                        browser {}
                        gitTool('')

                        // List of branches to build.
                        branches {
                            branchSpec {
                                // Specify the branches if you'd like to track a specific branch in a repository.
                                name(BRANCH_NAME)
                            }
                        }
                    }
                }
            }
        }
    }
}
