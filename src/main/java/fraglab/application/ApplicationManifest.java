package fraglab.application;

enum ApplicationManifest {

    GIT_HASH("git-hash"),
    SPRING_PROFILE("spring-profile");

    private String entry;

    ApplicationManifest(String entry) {
        this.entry = entry;
    }

    String getEntry() {
        return this.entry;
    }

}
