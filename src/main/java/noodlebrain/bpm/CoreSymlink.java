package noodlebrain.bpm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;

class CoreSymlink
{
    static void makeCoreSymlink(String pathName) throws IOException
    {
        // create symbolic link to core packages directory in "Packages" directory


        Path packageBase = PackageManager.InstalledPackageBase();

        // create Path objects for the core path and the destination path
        Path corePath = packageBase.resolve("Core");
        Path destPath = Paths.get(pathName,"Core").toAbsolutePath();
        Link(destPath, corePath);
    }


    public static void Link(Path newFile, Path linkTo) throws IOException {
        /* Windows requires admin rights to create symbolic links -
         * if the user is running Windows, copy the core packages directory instead
         */
        String os = System.getProperty("os.name");

        if (!os.contains("Windows"))
        {
            // Non-Windows OSes - create symbolic link
            try {
                Files.createSymbolicLink(newFile, linkTo);
            } catch (FileAlreadyExistsException e) {
                // If the file exists, great. Continue
            } 
        }
        else
        {
            // Windows - copy core packages directory and its contents
            FileUtils.copyDirectory(linkTo.toFile(), newFile.toFile());
        }
    }
}
