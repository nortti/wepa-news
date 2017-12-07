/* global Mustache */

$(document).ready(function () {
    refreshView();
});

// In addition to page load, done on each ajax call completion with restResponse set
function refreshView(restResponse = null) {
    // Reset everything except forms
    $('#errors').empty();
    $('#no-objects').hide();
    $('#object-table').hide();
    $('#object-table-body').empty();

    // Recreate list
    $.getJSON(baseUrl(), function (data) {
        if ($.isEmptyObject(data)) {
            $('#no-objects').show();
        } else {
            listObjects(data);
            $('#object-table').show();
        }
    });

    // Display errors if needed
    if (restResponse && restResponse.status === 400) {
        restResponse.responseJSON.forEach(function (error) {
            $('<li class="list-group-item list-group-item-danger">').text(error).appendTo('#errors');
        });
    } else { // Only reset forms on success
        $('#create-form')[0].reset();
        $('#update-form')[0].reset();
    }
}

function listObjects(data) {
    $.each(data, function (i, object) {
        var objectTemplate = $('#object-template').html();
        var objectHtml = Mustache.render(objectTemplate, object);
        $('#object-table-body').append(objectHtml);
    });
}

$('#create-form').on('submit', function (event) {
    event.preventDefault(); // html5 validation
    request('post', JSON.stringify(formToObject($(this))));
});

$('#update-form').on('submit', function (event) {
    event.preventDefault();
    request('put', JSON.stringify(formToObject($(this))));
    $('#modal').modal('hide');
});

$('#object-table-body').on('click', '.delete', function (event) {
    var id = event.target.value;
    request('delete', null, id);
});

$('#object-table-body').on('click', '.update', function (event) {
    var id = event.target.value;
    $.getJSON(baseUrl() + id, function(data) {
        var template = $('#modal-form-template').html();
        var html = Mustache.render(template, data);
        $('#update-form-inputs').html(html);
    });
});

function formToObject(form) {
    var formArray = form.serializeArray();
    var object = {};
    for (var i = 0; i < formArray.length; i++) {
        // 'false' is truthy which causes checkboxes to incorrctly
        //  be checked in modals when opened, so use booleans
        if (formArray[i]['value'] === 'false') {
            object[formArray[i]['name']] = false;
        } else {
            object[formArray[i]['name']] = formArray[i]['value'];
        }
    }
    return object;
}

// baseUrl is set in html (authors/categories) or separate js file (news)
function request(type,
                 data = null,
                 pathVars = '',
                 contentType = 'application/json; charset=utf-8',
                 processData = true) {
    $.ajax({
        url: baseUrl() + pathVars,
        contentType: contentType,
        type: type,
        data: data,
        processData: processData,
        complete: refreshView
    });
}
