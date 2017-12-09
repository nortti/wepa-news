/* global Mustache, refreshView, formToObject */

function newsUri() {
    return '/rest/news/';
}

function categoriesInNavigationUri() {
    return '/rest/categories/navigation';
}
$(document).ready(function () {
    loadNews();
});

function loadNews() {
    var newsCount = 5;
    // currentCategory() set in html, empty if all categories
    var newestNewsUri = newsUri() + currentCategory() + "newest/" + newsCount;
    var mostViewedNewsUri = newsUri() + currentCategory() + "most-viewed/" + newsCount;

    $.getJSON(newestNewsUri, function (news) {
        if ($.isEmptyObject(news)) {
            $('#no-news').show();
        } else {
            var newsHtml = newsListHtml(news, $('#news-item-template'));
            $('#newest-news').html(newsHtml);
            $('#news').show();
        }
    });

    $.getJSON(mostViewedNewsUri, function (news) {
        if (!$.isEmptyObject(news)) {
            var newsHtml = newsListHtml(news, $('#most-viewed-news-item-template'));
            $('#most-viewed-news').html(newsHtml);
        }
    });
}

function newsListHtml(news, template) {
    var html = "";
    $.each(news, function (i, newsItem) {
        html += Mustache.render(template.html(), newsItem);
    });
    return html;
}

$('#news').on('click', '.news', function (event) {
    var id = event.currentTarget.id;
    var readNewsUri = newsUri() + id + "/read";
    $.getJSON(readNewsUri, function(data) {
        var template = $('#news-modal-template').html();
        var html = Mustache.render(template, data);
        $('#modal-news').html(html);
    });
});