#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform float Time;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in vec2 texCoord0;
in vec4 vertexColor;
in float vertexDistance;

out vec4 fragColor;

void main() {
    vec2 uv = texCoord0;
    vec4 color = vertexColor;
    vec2 dirs[4] = vec2[](vec2(uv.x, uv.y), vec2(uv.y, uv.x), vec2(uv.x, 1.0-uv.y), vec2(uv.y, 1.0-uv.x));
    float finalBrightness = 0.0;
    for (int dir = 0; dir < 4; dir++) {
        vec2 currentDir = dirs[dir];
        float horizontal = currentDir.x;
        float vertical = currentDir.y;
        float direction = 1.0;
        float brightness = 1.0;
        for (int i = 0; i < 3; i++) {
            brightness /= 2;
            float initialShift = 0.025*float(i);
            float shift = initialShift;
            float timeShift = ((Time * (360.0 -(float(i) * (360.0/3.0))))* direction);
            float density = (12.0 *(1.0 + (1.0 - ((1.0 /5.0) * float(i)))));
            float xShift = (horizontal * 360.0 *density);
            float indexShift = (float(i) * 125.0);
            float sine = sin(radians(indexShift + xShift + timeShift));
            float sinePositive = ((sine + 1.0) /2.0);
            float scale = ((float(i) + 1.0) * 0.025);
            scale *= ((sin(radians(indexShift + timeShift)) + 1.0) / 2.0);
            shift += sinePositive * scale;
            if (vertical > shift) {
                finalBrightness = max((1.0 - clamp(((vertical - shift) / (0.05)), 0.0, 1.0)) * brightness, finalBrightness);
            } else {
                finalBrightness = max(brightness, finalBrightness);
            }
            direction = - direction;
        }
    }
    color = vec4(color.rgb*finalBrightness, color.a);
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
