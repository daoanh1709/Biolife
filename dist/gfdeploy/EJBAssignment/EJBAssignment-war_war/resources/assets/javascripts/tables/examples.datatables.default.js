

(function ($) {

    'use strict';

    var datatableInit = function () {

        $('#datatable-default').dataTable();

    };

    $(function () {
        datatableInit();
    });

}).apply(this, [jQuery]);

(function ($) {

    'use strict';

    var datatableInit = function () {

        $('#datatable-default1').dataTable();

    };

    $(function () {
        datatableInit();
    });

}).apply(this, [jQuery]);

(function ($) {

    'use strict';

    var datatableInit = function () {

        $('#datatable-deal').dataTable({
            order: [[0, 'desc']]
        });

    };

    $(function () {
        datatableInit();
    });

}).apply(this, [jQuery]);

(function ($) {

    'use strict';

    var datatableInit = function () {

        $('#datatable-orders').dataTable({
            order: [[0, 'desc']]
        });

    };

    $(function () {
        datatableInit();
    });

}).apply(this, [jQuery]);