PACKAGES = [
    'demo-package1',
    'demo-package2',
]

VIEWS = [[
    name: 'CENTOS6',
]]

folder('EL') {
    folder(BRANCH_NAME) {
        for (view in VIEWS) {
            listView(view.name) {
                recurse()
            }
        }
    }
}
