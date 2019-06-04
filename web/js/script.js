$( document ).ready(function() {
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
    })
});