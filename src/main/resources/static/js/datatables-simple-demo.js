window.addEventListener('DOMContentLoaded', event => {
    // Simple-DataTables
    // https://github.com/fiduswriter/Simple-DataTables/wiki

    const datatablesSimple = document.getElementById('datatablesSimple');
    if (datatablesSimple) {
        var table = new simpleDatatables.DataTable(datatablesSimple, {
            columns: [
                {select: [0,1,2,3,6], cellClass: "cell-width-10"},
                {select: [4,5], cellClass: "cell-width-25"}
            ]
        });
    }
});
// window.addEventListener('DOMContentLoaded', event => {
//     let table = $("#datatablesSimple").removeAttr("width").DataTable({
//         columnDefs: [{width: 200, targets: 0}],
//         fixedColumns: true
//     })
// });

// $(document).ready(function() {
//     let table = $("#datatablesSimple").removeAttr("width").DataTable({
//         columnDefs: [{width: 200, targets: 0}],
//         fixedColumns: true
//     })
//
//     new simpleDatatables.DataTable(table);
// });
