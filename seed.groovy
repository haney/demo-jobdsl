PACKAGES = [
    'demo-package1',
    'demo-package2',
]

ROOT = "EL/${BRANCH_NAME}"

folder(ROOT) {
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
                    github {
                        repoOwner('haney')
                        repository("${jobName}")
                        traits {
                            gitHubBranchDiscovery {
                                strategyId('1')
                            }
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
                        browser {
                            github {
                                repoUrl 'https://github.com/haney/demo-jobdsl.git'
                            }
                        }
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
