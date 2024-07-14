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

for (package in PACKAGES) {
    multibranchPipelineJob("${ROOT}/${package}") {
        branchSources {
            branchSource {
                source {
                    git {
                        remote("https://github.com/haney/${package}.git")
                    }
                }
            }
        }
    }
}
