from pathlib import Path

java_files = sorted(Path('src').rglob('*.java'))
print('processing', len(java_files))

for path in java_files:
    text = path.read_text(encoding='utf-8')
    out = []
    i = 0
    state = 'normal'
    while i < len(text):
        ch = text[i]
        nxt = text[i+1] if i+1 < len(text) else ''
        if state == 'normal':
            if ch == '/' and nxt == '/':
                state = 'line_comment'
                i += 2
                continue
            elif ch == '/' and nxt == '*':
                state = 'block_comment'
                i += 2
                continue
            elif ch == '"':
                state = 'string_double'
                out.append(ch)
                i += 1
                continue
            elif ch == "'":
                state = 'string_single'
                out.append(ch)
                i += 1
                continue
            else:
                out.append(ch)
                i += 1
                continue
        elif state == 'line_comment':
            if ch == '\n':
                state = 'normal'
                out.append(ch)
            i += 1
            continue
        elif state == 'block_comment':
            if ch == '*' and nxt == '/':
                state = 'normal'
                i += 2
            else:
                i += 1
            continue
        elif state == 'string_double':
            out.append(ch)
            if ch == '\\':
                if i+1 < len(text):
                    out.append(text[i+1])
                    i += 2
                    continue
            elif ch == '"':
                state = 'normal'
            i += 1
            continue
        elif state == 'string_single':
            out.append(ch)
            if ch == '\\':
                if i+1 < len(text):
                    out.append(text[i+1])
                    i += 2
                    continue
            elif ch == "'":
                state = 'normal'
            i += 1
            continue
    new_text = ''.join(out)
    if new_text != text:
        path.write_text(new_text, encoding='utf-8')
        print(f'Updated {path}')
