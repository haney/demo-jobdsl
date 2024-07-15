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
    if (BRANCH_NAME == 'main') {
        BUILD_BRANCHES = "^(main|${jobName}.*\\.el[0-9_]+)\$"
    }
    else {
        BUILD_BRANCHES = '^$'
    }

    multibranchPipelineJob("${ROOT}/${jobName}") {
        branchSources {
            branchSource {
                source {
                    git {
                        remote("https://github.com/haney/${jobName}.git")
                        traits {
                            gitBranchDiscovery() 
                        }
                    }
                }
                strategy {
                    namedBranchesDifferent {
                        defaultProperties {
                            suppressAutomaticTriggering {
                                triggeredBranchesRegex(BUILD_BRANCHES)
                            }
                        }
                    }
                }
            }
        }
        triggers {
            if (BRANCH_NAME == 'main') {
                periodicFolderTrigger {
                    interval '1m'
                }
                cron {
                    spec('* * * * *')
                }
            }
        }
        factory {
            remoteJenkinsFileWorkflowBranchProjectFactory {
                remoteJenkinsFile("package.jenkinsfile")
                localMarker('')
                remoteJenkinsFileSCM {
                    gitSCM {
                        userRemoteConfigs {
                            userRemoteConfig {
                                url('https://github.com/haney/demo-jobdsl.git')
                                credentialsId('github-p4components-app')
                                name("origin")
                                refspec("+refs/heads/${BRANCH_NAME}:refs/remotes/origin/${BRANCH_NAME}")
                            }
                        }
                        matchBranches(false)
                        fallbackBranch(BRANCH_NAME)
                        lookupInParameters(false)
                        browser {}
                        gitTool('')

                        branches {
                            branchSpec {
                                name(BRANCH_NAME)
                            }
                        }
                    }
                }
            }
        }
    }
}
