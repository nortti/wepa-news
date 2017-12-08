/* global Mustache, refreshView, formToObject */

function baseUrl() {
    return '/rest/news/';
}

function authorsUrl() {
    return '/rest/authors/';
}

function categoriesUrl() {
    return '/rest/categories';
}

$(document).ready(function() {
    createSelectOptions(authorsUrl(), $('#author-ids'));
    createSelectOptions(categoriesUrl(), $('#category-ids'));
});

function createSelectOptions(url, selectElement, existingRelationships = null) {
    $.getJSON(url, function(data) {
        if ($.isEmptyObject(data)) {
            selectElement.replaceWith(
                "<p>There are none, go add some first.</p>");
        } else {
            $.each(data, function(i, object) {
                var select = false;
                // When updating, existingRelationships contains the objects with an exisiting
                // relationship to this one that should be pre-checked in the modal
                if (existingRelationships) {
                    for (var existingRelationship of
                            existingRelationships) {
                        if (existingRelationship.id === object.id) {
                            select = true;
                            break;
                        }
                    }
                }
                object['selected'] = select;
                var optionTemplate = $(
                    '#select-option-template').html();
                var optionHtml = Mustache.render(optionTemplate,
                    object);
                $(selectElement).append(optionHtml);
            });
        }
    });
}

// override some handlers from common.js next

// need to include select-options logic in this handler
$('#object-table-body .update').unbind('click');
$('#object-table-body').on('click', '.update', function(event) {
    var id = event.target.value;
    $.getJSON(baseUrl() + id, function(data) {
        var template = $('#modal-form-template').html();
        var html = Mustache.render(template, data);
        $('#update-form-inputs').html(html);
        createSelectOptions(authorsUrl(),
            $('#update-author-ids'), data.authors);
        createSelectOptions(categoriesUrl(),
            $('#update-category-ids'), data.categories);
    });
});

// need to send these differently (with FormData) due to the image
$('#create-form, #update-form').unbind('submit');
$('#create-form').on('submit', function(event) {
    event.preventDefault();
    var formData = new FormData($(this)[0]);
    request('post', formData, '', false, false);
});
$('#update-form').on('submit', function(event) {
    event.preventDefault();
    var formData = new FormData($(this)[0]);
    request('post', formData, '', false, false);
    $('#modal').modal('hide');
});
