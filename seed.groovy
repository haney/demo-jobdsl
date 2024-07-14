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
