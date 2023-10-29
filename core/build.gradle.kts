plugins {
    id("fr.stardustenterprises.rust.wrapper")
}

rust {
    command = "cargo"

    environment = mapOf("RUSTUP_TOOLCHAIN" to "nightly")

    outputs = mapOf("" to System.mapLibraryName("sodium_core"))

    outputDirectory = "META-INF/natives"

    profile = "release"
}