set -e

PREV="${VERCEL_GIT_PREVIOUS_COMMIT_SHA}"
CURR="${VERCEL_GIT_COMMIT_SHA}"

if [ -z "$CURR" ]; then
  echo "No current commit SHA from Vercel; proceed build"
  exit 1
fi

if [ -z "$PREV" ]; then
  echo "No previous commit SHA; proceed build"
  exit 1
fi

CHANGED="$(git diff --name-only "$PREV" "$CURR" | grep -E '^(frontend/|vercel\.json|package\.json|pnpm-lock\.yaml|yarn\.lock)')"

if [ -z "$CHANGED" ]; then
  echo "No frontend-relevant changes; skip build"
  exit 0
else
  echo "Frontend changes detected:"
  echo "$CHANGED"
  exit 1
fi
