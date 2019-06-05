$(document).ready(function() {
    $('body').on('click', '.delete-input', function () {
        $(this).closest('.form-group').remove();
    });

    $('.add-input-list').on('click', function () {
        var type = $(this).data('type');

        var template = '<div class="form-group d-flex">\n' +
            '                            <input name="{type}" type="text" class="form-control" placeholder="Enter value" value="">\n' +
            '                            <button type="button" class="btn btn-danger delete-input"><span class="oi oi-trash"></span></button>\n' +
            '                        </div>';

        var inputBlock = template.replace('{type}', type);

        $(this).closest('.list-block').find('.inputs').append(inputBlock);
    });

    $('body').on('click', '.delete-position', function () {
        var positionsList = $(this).closest('.positions-list');

        $(this).closest('.position').remove();

        var positionsLeft = positionsList.find('.position').length;

        if (positionsLeft === 0) {
            positionsList.closest('.organization').remove();
        }
    });

    $('body').on('click', '.add-position', function () {
        var type = $(this).data('type');

        var template = '<div class="position">\n' +
            '<div class="d-flex mb-2">\n' +
            '  <button type="button" class="btn btn-danger delete-position mr-2"><span class="oi oi-trash"></span></button>' +
            '  <input name="' + type + '" type="text" placeholder="Title" class="form-control mr-2" required>' +
            '  <input name="' + type + '" type="date" placeholder="Period from" class="form-control mr-2" required>' +
            '  <input name="' + type + '" type="date" placeholder="Period to" class="form-control">' +
            '</div>' +
            '<div>' +
            '  <textarea name="' + type + '" class="form-control" rows="5"></textarea>' +
            '</div>' +
            '<input name="' + type + '" type="hidden" value="---">' +
            '<hr>' +
            '</div>';

        $(this).closest('.positions').find('.positions-list').append(template);
    });

    $('body').on('click', '.delete-organization', function () {
        $(this).closest('.organization').remove();
    });

    $('.add-organization').on('click', function () {
        var type = $(this).data('type');

        var template = '<div class="organization form-group">' +
            '    <div class="d-flex mb-3">' +
            '        <input name="' + type + '" type="text" placeholder="Title" class="form-control mr-2" required>' +
            '        <button type="button" class="btn btn-danger delete-organization"><span class="oi oi-trash"></span></button>' +
            '    </div>' +
            '    <div class="positions pl-4">' +
            '        <div class="positions-list">' +
            '<div class="position">' +
            '<div class="d-flex mb-2">' +
            '  <button type="button" class="btn btn-danger delete-position mr-2"><span class="oi oi-trash"></span></button>' +
            '  <input name="' + type + '" type="text" placeholder="Title" class="form-control mr-2" required>' +
            '  <input name="' + type + '" type="date" placeholder="Period from" class="form-control mr-2" required>' +
            '  <input name="' + type + '" type="date" placeholder="Period to" class="form-control">' +
            '</div>' +
            '<div>' +
            '  <textarea name="' + type + '" class="form-control" rows="5"></textarea>' +
            '</div>' +
            '<input name="' + type + '" type="hidden" value="---">' +
            '<hr>' +
            '</div>' +
            '        </div>' +
            '        <input name="' + type + '" type="hidden" value="-">' +
            '        <button type="button" class="btn btn-success add-position" data-type="' + type + '"><span class="oi oi-plus"></span></button>' +
            '    </div>' +
            '</div>';

        $(this).closest('.organization-block').find('.organizations-list').append(template);
    });
});