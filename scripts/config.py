__author__ = 'will czifro'

REPO_TYPES = [
    'SNAPSHOT',
    'STAGING',
    'RELEASE'
]


RELEASE_BRANCH_PREFIX = 'rel/'
# Any branch marked as beta is at staging
STAGING_BRANCH_PATTERN = '[a-zA-Z0-9_\\.-]*beta([_])?\\d{0,}[a-zA-Z0-9_\\.-]*'
# Add specific branches for snapshot deployment
SNAPSHOT_BRANCHES = [
    'develop',
    'vNext_0_5_0'
]

MAVEN_SERVER = "http://45.55.49.123:8081"

SNAPSHOT_PATH = "/nexus/content/repositories/snapshots"
STAGING_PATH = "/nexus/content/repositories/staging"
RELEASE_PATH = "/nexus/content/repositories/release"

BETA = True
BETA_VAL = 3