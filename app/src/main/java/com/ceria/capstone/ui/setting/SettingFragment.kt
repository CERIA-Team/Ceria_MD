package com.ceria.capstone.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentSettingBinding
import com.ceria.capstone.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {
    private val viewModel: SettingViewModel by viewModels()
    override fun setupUI() {

    }

    override fun setupListeners() {
        with(binding) {
            tvLogout.setOnClickListener {
                showLogoutConfirmationDialog()
            }
            language.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
    }
    private fun showLogoutConfirmationDialog() {
        val customView = layoutInflater.inflate(R.layout.dialog_logout_confirmation, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(customView)
            .create()

        with(customView) {
            findViewById<TextView>(R.id.tvLogoutTitle).text = getString(R.string.confirmation_logout_title)
            findViewById<TextView>(R.id.tvLogoutMessage).text = getString(R.string.confirmation_logout_message)
            findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            findViewById<Button>(R.id.btnConfirm).setOnClickListener {
                viewModel.logout()
                findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToLoginFragment())
                dialog.dismiss()
            }
        }

        dialog.show()
    }


    override fun setupObservers() {

    }

}