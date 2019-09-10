/**
    Orange Baah Box
    Copyright (C) 2017 – 2019 Orange SA

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.orange.labs.orangetrainingbox.ui.settings

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.orange.labs.orangetrainingbox.utils.properties.PropertiesKeys
import android.app.Activity
import android.content.Intent
import androidx.preference.SeekBarPreference
import com.orange.labs.orangetrainingbox.utils.properties.isDemoFeatureEnabled
import com.orange.labs.orangetrainingbox.utils.properties.readSheepDefaultConfiguration


/**
 * Activity dedicated to preferences
 *
 * @author Pierre-Yves Lapersonne
 * @since 24/05/2019
 * @version 1.2.0
 */
class SettingsActivity : AppCompatActivity() {


    /**
     * A simple companion object
     */
    companion object {

        /**
         * The release
         */
        private var versionRelease: String? = null

    }


    /**
     * Activity lifecycle.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        versionRelease = buildReleaseString()
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }

    /**
     * Activity lifecycle.
     */
    override fun onBackPressed() {
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    /**
     * Procedure which will build a user-friendly string describing the version of the app
     *
     * @return String A string with version name and version code
     */
    private fun buildReleaseString(): String {
        val sb = StringBuilder()
        try {
            val pi = packageManager.getPackageInfo(packageName, 0)
            val versionName = pi.versionName
            sb.append("Version ").append(versionName)
            val versionCode = pi.versionCode
            sb.append(" - Build ").append(versionCode)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return sb.toString()
    }


    /**
     * Fragment dedicated to the preferences
     *
     * @author Pierre-Yves Lapersonne
     * @since 24/05/2019
     * @version 1.1.0
     */
    class SettingsFragment : PreferenceFragmentCompat() {

        /**
         * Fragment lifecycle
         */
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(com.orange.labs.orangetrainingbox.R.xml.preferences, rootKey)
            prepareDemoPreference()
            prepareVersionPreference()
            prepare3rdPartyPreference()
            prepareDifficultyFactorPreference()
            prepareSheepGamePreference()
        }

        /**
         * If enabled in config file, display the demo switcg
         */
        private fun prepareDemoPreference() {
            val demoPreference = findPreference("preferences_demo_mode_enabled")
            if (activity?.isDemoFeatureEnabled() == false) {
                demoPreference.isVisible = false
            } else {
                demoPreference.setOnPreferenceChangeListener { _, newValue ->
                    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                    val editor = preferences.edit()
                    editor.putBoolean(PropertiesKeys.ENABLE_DEMO_FEATURE.key, newValue as Boolean)
                    editor.apply()
                    true
                }
            }
        }

        /**
         *
         */
        private fun prepareVersionPreference() {
            val versionPreference = findPreference("pref_key_about_app")
            versionPreference.summary = versionRelease
        }

        /**
         * Prepares the widget related to 3rd party licenses
         */
        private fun prepare3rdPartyPreference() {
            val licensesPreference = findPreference("pref_key_about_licenses")
            licensesPreference.setOnPreferenceClickListener {
                LicensesDisplayer().displayLicenses(activity!!)
                true
            }
        }

        /**
         *
         */
        private fun prepareDifficultyFactorPreference(){
            val difficultyPreference = findPreference("pref_key_settings_sensors_difficulty")
            // TODO If never defined use default value from properties
            difficultyPreference.setOnPreferenceChangeListener { _, newValue ->
                val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                val editor = preferences.edit()
                editor.putInt(PropertiesKeys.DIFFICULTY_FACTOR.key, newValue as Int)
                editor.apply()
                true
            }
        }

        /**
         * Defines minimum, maximum and defaults values for the slider
         */
        private fun prepareSheepGamePreference() {
            val sheepGameDefaultConfiguration = activity!!.readSheepDefaultConfiguration()
            val numberOfFencesPreferences = findPreference("pref_key_settings_game_sheep_fences_number") as SeekBarPreference
            // TODO If never defined use default value from properties
            numberOfFencesPreferences.setOnPreferenceChangeListener { _, newValue ->
                val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                val editor = preferences.edit()
                editor.putInt("pref_key_settings_game_sheep_fences_number", newValue as Int)
                editor.apply()
                true
            }
            numberOfFencesPreferences.min = 1
            numberOfFencesPreferences.max = sheepGameDefaultConfiguration.defaultMaxFencesCount
            numberOfFencesPreferences.setDefaultValue(sheepGameDefaultConfiguration.defaultFencesCount)
        }

    } // End of class MySettingsFragment

} // End of class SettingsActivity : AppCompatActivity()

