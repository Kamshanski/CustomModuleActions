package swing

import javax.swing.JTextField

/** Надо учитывать, что текст null и "" одинаков */
fun JTextField.setTextIfDiffers(text: String) {
	val curText = this.text

	if (curText == text || (curText.isNullOrEmpty() && text.isEmpty())) {
		return
	}

	this.text = text
}