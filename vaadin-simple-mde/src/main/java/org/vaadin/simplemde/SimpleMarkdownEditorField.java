package org.vaadin.simplemde;

import com.vaadin.data.Buffered;
import com.vaadin.data.Validator;
import com.vaadin.shared.util.SharedUtil;
import com.vaadin.ui.Component;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Wrapper for make {@link SimpleMarkdownEditor} a {@link com.vaadin.ui.Field}
 *
 * Created by Luca De Felici.
 */
public class SimpleMarkdownEditorField extends FluidCustomField<String> {

    private SimpleMarkdownEditor markdownEditor;

    public SimpleMarkdownEditorField() {
        super();
        init();
    }

    @Override
    public Class<? extends String> getType() {
        return String.class;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        this.markdownEditor.setReadOnly(readOnly);
    }

    @Override
    public void commit() throws Buffered.SourceException, Validator.InvalidValueException {
        setInternalValue(markdownEditor.getValue());
        super.commit();
    }

    @Override
    public void discard() throws Buffered.SourceException {
        super.discard();
        markdownEditor.setValue(getInternalValue());
    }

    @Override
    protected void setInternalValue(String newValue) {
        super.setInternalValue(newValue);
        markdownEditor.setValue(newValue);
    }

    @Override
    protected Component initContent() {
        return markdownEditor;
    }

    private void init() {
        this.markdownEditor = new SimpleMarkdownEditor();
        this.markdownEditor.setShowStatus(false);
        this.markdownEditor.setSizeFull();
        this.markdownEditor.setLineWrapping(false);
        this.markdownEditor.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        this.markdownEditor.addValueChangeListener(e -> {
            String newValue = e.getValue();
            if (!SharedUtil.equals(newValue, getInternalValue())) {
                SimpleMarkdownEditorField.super.setInternalValue(newValue);
                SimpleMarkdownEditorField.this.fireValueChange(true);
            }
        });
    }

}
