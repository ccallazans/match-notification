package utils

import (
	"context"
	"database/sql"
)

func AddTxCtx(ctx context.Context, tx *sql.Tx) context.Context {
	return context.WithValue(ctx, "tx", tx)
}
