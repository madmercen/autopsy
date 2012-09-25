/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.datamodel;

import java.awt.Component;
import java.util.logging.Level;
import org.sleuthkit.autopsy.coreutils.Logger;
import javax.swing.JOptionPane;
import org.openide.LifecycleManager;
import org.openide.modules.ModuleInstall;
import org.sleuthkit.datamodel.SleuthkitJNI;

/**
 * Installer checks that the JNI library is working when the module is loaded.
 */
public class Installer extends ModuleInstall {
    
    public Installer() {
        super();
    }


    @Override
    public void validate() throws IllegalStateException {
        /*
         * The NetBeans API specifies that a module should throw an IllegalStateException
         * if it can't be initalized, but NetBeans doesn't handle that behaviour
         * well (it just disables the module, and all dependant modules, on the
         * current and all subsequent application launches). Hence, we deal with
         * it manually.
         *
         */


        // Check that the the Sleuth Kit JNI is working by getting the Sleuth Kit version number
        Logger logger = Logger.getLogger(Installer.class.getName());
        try {
            String skVersion = SleuthkitJNI.getVersion();

            if (skVersion == null) {
                throw new Exception("Sleuth Kit JNI test call returned without error, but version string was null!");
            } else if (skVersion.length() == 0) {
                throw new Exception("Sleuth Kit JNI test call returned without error, but version string was \"\"!");
            } else {
                logger.log(Level.CONFIG, "Sleuth Kit Version: {0}", skVersion);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error calling Sleuth Kit library (test call failed)", e);


            // Normal error box log handler won't be loaded yet, so show error here.
            final Component parentComponent = null; // Use default window frame.
            final String message = "Problem with Sleuth Kit JNI. Test call failed!\n\nDetails: " + e.toString();
            final String title = "Fatal Error!";
            final int messageType = JOptionPane.ERROR_MESSAGE;

            JOptionPane.showMessageDialog(
                    parentComponent,
                    message,
                    title,
                    messageType);


            // exit after user exits the error dialog box
            LifecycleManager.getDefault().exit();
        }

    }
}
