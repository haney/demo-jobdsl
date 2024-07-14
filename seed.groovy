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
    }
}
