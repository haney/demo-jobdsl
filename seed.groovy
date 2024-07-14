PACKAGES = [
    'demo-package1',
    'demo-package2',
]

VIEWS = [[
    name: 'CENTOS6',
]]

folder('EL') {
    folder(JENKINS_BRANCH) {
        for (view in VIEWS) {
            listView(view.name) {
                recruse()
            }
        }
    }
}
