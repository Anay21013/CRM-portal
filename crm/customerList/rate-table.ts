import { ColumnDef } from "../../common-utils/components/table/table.config";

export const rateTable = (cellParams:any=null):ColumnDef[]=>{
    return[
        {
            field: 'id',
            header: 'ID',
            
            hide: true,
            download: false,
            sortable: true,
        },
        {
            field: 'roleName',
            header: 'Role name',
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
            field: 'roleRate',
            header: 'Role rate',
            hide: false,
            download: true,
            sortable: true,
        },
        {
            field: 'roleRateFrequency',
            header: 'Role rate frequency',
            hide: false,
            download: true,
            sortable: true,
        },
        {
            field: 'currencyCode',
            header: 'Currency Code',
            hide: false,
            download: true,
            sortable: true,
        },
        {
            field: 'startDate',
            header: 'State Date',
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