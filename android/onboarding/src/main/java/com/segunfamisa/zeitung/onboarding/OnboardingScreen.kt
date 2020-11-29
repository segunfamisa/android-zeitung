package com.segunfamisa.zeitung.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.segunfamisa.zeitung.common.theme.colors
import com.segunfamisa.zeitung.common.theme.preview.ThemedPreview
import com.segunfamisa.zeitung.common.theme.typography
import kotlinx.coroutines.FlowPreview

@FlowPreview
@Composable
fun OnboardingContent(
    onboardingViewModel: Lazy<OnboardingViewModel>,
    onContinue: (String) -> Unit,
    onApiTokenChange: (String) -> Unit
) {
    Scaffold {
        val continueButtonEnabled =
            onboardingViewModel.value.continueButtonEnabled.observeAsState(false)
        OnboardingScreen(
            continueButtonEnabled = continueButtonEnabled,
            onContinue = onContinue,
            onApiTokenChange = onApiTokenChange
        )
    }
}

@Composable
fun OnboardingScreen(
    continueButtonEnabled: State<Boolean>,
    onContinue: (String) -> Unit,
    onApiTokenChange: (String) -> Unit
) {
    var apiKey by savedInstanceState(saver = TextFieldValue.Saver) { TextFieldValue() }
    Box(
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
        alignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.wrapContentSize(align = Alignment.Center).padding(72.dp)
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                asset = vectorResource(id = R.drawable.onboarding_icon)
            )
            Spacer(modifier = Modifier.preferredHeight(16.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.onboarding_prompt),
                style = typography().h5,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.preferredHeight(16.dp))
            OutlinedTextField(
                value = apiKey,
                onValueChange = {
                    apiKey = it
                    onApiTokenChange(it.text)
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Go
                ),
                onImeActionPerformed = { imeAction, softwareKeyboardController ->
                    if (imeAction == ImeAction.Go) {
                        softwareKeyboardController?.hideSoftwareKeyboard()
                        onContinue(apiKey.text)
                    }
                },
                textStyle = typography().caption.copy(color = contentColorFor(color = colors().background)),
                activeColor = colors().secondary,
                label = { Text(text = stringResource(id = R.string.api_key_text_prompt)) }
            )
            Spacer(modifier = Modifier.preferredHeight(16.dp))
            Button(
                onClick = { onContinue(apiKey.text) },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonConstants.defaultButtonColors(),
                enabled = continueButtonEnabled.value
            ) {
                Text(text = stringResource(id = R.string.onboarding_continue))
            }
        }
    }
}

@Preview("Preview onboarding screen")
@Composable
private fun PreviewOnboardingScreen() {
    ThemedPreview {
        OnboardingScreen(
            continueButtonEnabled = mutableStateOf(true),
            onContinue = {},
            onApiTokenChange = {}
        )
    }
}

@Preview("Preview onboarding screen dark mode")
@Composable
private fun PreviewOnboardingScreenDarkMode() {
    ThemedPreview(darkTheme = true) {
        OnboardingScreen(
            continueButtonEnabled = mutableStateOf(false),
            onContinue = {},
            onApiTokenChange = {}
        )
    }
}
