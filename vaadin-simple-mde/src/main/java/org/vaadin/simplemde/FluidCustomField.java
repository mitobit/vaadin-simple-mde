package org.vaadin.simplemde;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.ui.CustomField;

import java.util.Locale;

/**
 * Useful method to enable fluid interface.
 *
 * Created by Luca De Felici.
 */
public abstract class FluidCustomField<FT> extends CustomField<FT> {

    private boolean modified = false;

    {
        addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                FluidCustomField.this.modified = true;
            }
        });
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    @Override
    public void commit() throws SourceException, Validator.InvalidValueException {
        this.modified = false;
        super.commit();
    }

    @Override
    public void discard() throws SourceException {
        this.modified = false;
        super.discard();
    }

    public FluidCustomField<FT> withStyleName(String style) {
        addStyleName(style);
        return this;
    }

    public FluidCustomField<FT> withWidth(String width) {
        setWidth(width);
        return this;
    }

    public FluidCustomField<FT> withHeight(String height) {
        setHeight(height);
        return this;
    }

    public FluidCustomField<FT> withSizeFull() {
        setSizeFull();
        return this;
    }

    public FluidCustomField<FT> withCaption(String caption) {
        setCaption(caption);
        return this;
    }

    public FluidCustomField<FT> withReadOnly(final boolean readOnly) {
        setReadOnly(readOnly);
        return this;
    }

    public FluidCustomField<FT> widthLocale(final Locale locale) {
        setLocale(locale);
        return this;
    }

    public FluidCustomField<FT> widthValidationVisible(final boolean validateAutomatically) {
        setValidationVisible(validateAutomatically);
        return this;
    }

}
