window.org_vaadin_simplemde_SimpleMarkdownEditor = function () {

    var elem = this.getElement(), self = this;
    var simplemde, timeout, icons = [];

    this.setMarkdownText = function (text) {
        if (typeof simplemde !== 'undefined') {
            simplemde.value(text)
        }
    };

    this.onStateChange = function () {

        var state = this.getState();

        if (typeof simplemde === 'undefined') {
            textarea = document.createElement('textarea');
            textarea.classList.add('simple-mde-textarea');
            elem.appendChild(textarea);

            var hideIcons = [];
            if (typeof state.hideIcons !== 'undefined') {
                state.hideIcons.forEach(function (icon) {
                    hideIcons.push(icon.toLowerCase()
                        .replace(/_/gi, '-'))
                })
            }

            simplemde = new SimpleMDE({
                element: textarea,
                status: (state.showStatus ? ['lines', 'words', 'cursor'] : false),
                spellChecker: state.spellChecker,
                lineWrapping: state.lineWrapping,
                initialValue: state.markdownText,
                hideIcons: hideIcons
            });

            var toolbarElements = simplemde.toolbarElements;
            for (var icon in toolbarElements) {
                if (toolbarElements.hasOwnProperty(icon)) {
                    icons.push(icon);
                }
            }

            if (!state.showStatus) {
                elem.classList.add('hidden-status')
            }

            simplemde.codemirror.on('change', function () {
                clearTimeout(timeout);
                timeout = setTimeout(function () {
                    self.getRpcProxy().textChanged(simplemde.value())
                }, state.changeTimeOut);
            });
        }

        if (state.readOnly) {
            if (!simplemde.isPreviewActive()) {
                // set mde in preview mode
                simplemde.togglePreview();
            }
            // iterate on toolbars and remove
            var tb = elem.getElementsByClassName('editor-toolbar');
            for (idx = tb.length - 1; idx >= 0; --idx) {
                tb[idx].remove();
            }
        } else {
            if (simplemde.isPreviewActive()) {
                // set mde in edit mode
                simplemde.togglePreview();
            }
            simplemde.createToolbar(icons);
        }

    };

};