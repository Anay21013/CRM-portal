import { ColumnDef } from "../../common-utils/components/table/table.config";

export const masterTable = (cellParams:any=null):ColumnDef[]=>{
    return[
        {
            field: 'id',
            header: 'ID',
            
            hide: true,
            download: false,
            sortable: true,
        },
        {
            field: 'name',
            header: 'Name',
            cellClicked: (data: any) => cellParams.cellClicked(data),
            hide: false,
            download: true,
            sortable: true,
            filter: {
                type: "text",
                isEnabled: true,
                numberOnly: false
            }
        },
        {
            field: 'email',
            header: 'Email',
            hide: false,
            download: true,
            sortable: true,
        },
        {
            field: 'phone',
            header: 'Phone',
            hide: false,
            download: true,
            sortable: true,
        },
        {
            field: 'addressLine1',
            header: 'Address Line 1',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'addressLine2',
            header: 'Address Line 2',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'city',
            header: 'City',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'state',
            header: 'State',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'zip',
            header: 'ZIP',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'paymentTerms',
            header: 'Payment Terms',
            hide: false,
            download: true,
            sortable: true,
        },
        {
            field: 'country',
            header: 'Country',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'createdBy',
            header: 'Created By',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'orgId',
            header: 'orgId',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'createdDatetime',
            header: 'Created Date Time',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'customers',
            header: 'Customers',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'rateChart',
            header: 'Rate Chart',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'lastModifiedBy',
            header: 'Last Modified By',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'lastModifiedDatetime',
            header: 'Last Modified Date time',
            hide: true,
            download: true,
            sortable: true,
        },
    ];
};